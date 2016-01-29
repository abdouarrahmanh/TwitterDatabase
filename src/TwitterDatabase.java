import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwitterDatabase extends HashMap {


    Map<String, TwitterUser> name2User;
    Map<Tweet, TwitterUser> tweet2User;
    Map<String, Set<Tweet>> word2Tweet;
    Map<TwitterUser, Set<Tweet>> user2Tweet;

    TwitterDatabase(String datfile) {

        TweetReader TR = new TweetReader(datfile);
        name2User = new HashMap<>();
        tweet2User = new HashMap<>();
        word2Tweet = new HashMap<>();
        user2Tweet = new HashMap<>();


        while (TR.advance()){
            String user = TR.getTweeterID();
            String tweet = TR.getTweet();
            addOrGetUser(user);
            addTweet(tweet,addOrGetUser(user));
        }
    }

    public TwitterDatabase(String datfile, Map<String, TwitterUser> name2User, Map<Tweet, TwitterUser> tweet2User,
                           Map<String, Set<Tweet>> word2Tweet, Map<TwitterUser, Set<Tweet>> user2Tweet) {

        TweetReader TR = new TweetReader(datfile);
        this.name2User = name2User;
        this.tweet2User = tweet2User;
        this.word2Tweet = word2Tweet;
        this.user2Tweet = user2Tweet;

        while (TR.advance()) {
            String user = TR.getTweeterID();
            String tweet = TR.getTweet();
            addOrGetUser(user);
            addTweet(tweet, addOrGetUser(user));
        }

    }

    public static void main(String[] args) {
        TwitterDatabase TD = new TwitterDatabase("uofmtweets.dat");
        TwitterDatabase TD2 = new TwitterDatabase("uofmtweets.dat", new TreeMap<>(), new TreeMap<>(),
                new TreeMap<>(), new TreeMap<>());
        TwitterDatabase TD3 = new TwitterDatabase("uofmtweets.dat", new LinearScanMap<>(), new LinearScanMap<>(),
                new LinearScanMap<>(), new LinearScanMap<>());

        System.out.println("\n"+"--Part 1--");
        System.out.println("name table size = " + TD.getNameTable().size());
        System.out.println("tweet table size = " + TD.getTweetTable().size());
        System.out.println("word table size = " + TD.getWordTable().size());
        System.out.println("user table size = " + TD.getUserTable().size());

        System.out.println("\n"+"--Part 2--");
        System.out.println("Top 10 results of getTweetCounts" + "\n" + formatList(TD.getTweetCounts()));
        System.out.println("Top 10 results of getWordCounts" + "\n" + formatList(TD.getWordCount()));
        System.out.println("Top 10 results of getWordUsage(minnesota)" + "\n" + formatList(TD.getWordUsage("minnesota")));
        System.out.println("Top 10 results of getWordUsage(university)" + "\n" + formatList(TD.getWordUsage("university")));

        System.out.println("\n"+"--Part 3--");
        System.out.println("Hashmap");
        TD.getTweetCounts();
        TD.getWordCount();
        TD.getWordUsage("minnesota");
        TD.getWordUsage("university");
        System.out.println("Treemap");
        TD2.getTweetCounts();
        TD2.getWordCount();
        TD2.getWordUsage("minnesota");
        TD2.getWordUsage("university");
        System.out.println("LinearScanMap");
        TD3.getTweetCounts();
        TD3.getWordCount();
        TD3.getWordUsage("minnesota");
        TD3.getWordUsage("university");
    }

    public Map getNameTable() {
        return name2User;
    }

    public Map getTweetTable() {
        return tweet2User;
    }

    public Map getWordTable() {
        return word2Tweet;
    }

    public Map getUserTable() {
        return user2Tweet;
    }

    public TwitterUser addOrGetUser(String name) {
        if (!name2User.containsKey(name)) {
            TwitterUser newUser = new TwitterUser(name);
            name2User.put(name, newUser);
            HashSet userTweets = new HashSet();
            user2Tweet.put(newUser, userTweets);
            return newUser;
        } else {
            return (TwitterUser) name2User.get(name);
        }
    }

    public static String formatList(List<ItemCount> list2Format) {
        String formatList = list2Format.subList(0,9).toString();
        formatList = formatList.replace(",", "");
        formatList = formatList.replace("[", "");
        formatList = formatList.replace("]", "");
        formatList = formatList.trim();
        return formatList;

    }
    public int addWord(String word, Tweet tweet) {
        if (!word2Tweet.containsKey(word)) {
            HashSet set_Tweets = new HashSet();
            set_Tweets.add(tweet);
            word2Tweet.put(word, set_Tweets);
            return set_Tweets.size();
        } else {
            Set<Tweet> value = word2Tweet.get(word);
            value.add(tweet);
            return value.size();

        }
    }

    public Tweet addTweet(String msg, TwitterUser user) {
        Tweet new_Tweet = new Tweet(msg);
        tweet2User.put(new_Tweet, user);
        Set<Tweet> user_Tweets = (Set<Tweet>) user2Tweet.get(user);
        user_Tweets.add(new_Tweet);
        Set<String> all_Words = new_Tweet.getWords();
        for (String word : all_Words) {
            this.addWord(word, new_Tweet);
        }
        return new_Tweet;

    }

    public List<ItemCount> getTweetCounts() {
        long start = System.currentTimeMillis();
        List<ItemCount> userTweets = new ArrayList<>();

        for (TwitterUser key : user2Tweet.keySet()) {
            ItemCount count = new ItemCount(key, user2Tweet.get(key).size());
            userTweets.add(count);
        }
        Collections.sort(userTweets);
        Collections.reverse(userTweets);
        long stop = System.currentTimeMillis();

        long duration = stop - start;
        System.out.println("getTweetCounts() took " + duration + "ms to execute");
        return userTweets;
    }


    public List<ItemCount> getWordCount() {
        long start = System.currentTimeMillis();
        List<ItemCount> wordCounts = new ArrayList<>();

        for (String key : word2Tweet.keySet()) {
            Set Tweets = word2Tweet.get(key);
            ItemCount count = new ItemCount(key, Tweets.size());
            wordCounts.add(count);
        }
        Collections.sort(wordCounts);
        Collections.reverse(wordCounts);
        long stop = System.currentTimeMillis();
        long duration = stop - start;
        System.out.println("getWordCounts() took " + duration + "ms to execute");
        return wordCounts;
    }

    public List<ItemCount> getWordUsage(String word) {
        long start = System.currentTimeMillis();
        List<ItemCount> wordUsage = new ArrayList<>();
        word =word.toLowerCase();

        for(TwitterUser user:user2Tweet.keySet()){
            int tracker = 0;
            for(Tweet tweet:user2Tweet.get(user)){
                String tweetContent = tweet.getContent();
               tweetContent = tweetContent.toLowerCase();
                Pattern p = Pattern.compile(word);
                Matcher m = p.matcher(tweetContent);
                while (m.find()){
                    tracker++;
                }
            }
            ItemCount count = new ItemCount(user,tracker);
            wordUsage.add(count);
        }


        Collections.sort(wordUsage);
        Collections.reverse(wordUsage);
        long stop = System.currentTimeMillis();
        long duration = stop - start;
        System.out.println("getWordUsage() took " + duration + "ms to execute");
        return wordUsage;
    }

}