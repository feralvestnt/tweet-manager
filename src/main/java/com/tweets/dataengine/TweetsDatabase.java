package com.tweets.dataengine;

import com.tweets.builder.IdiomBuilder;
import com.tweets.builder.TweetBuilder;
import com.tweets.builder.UserBuilder;
import com.tweets.entities.Idiom;
import com.tweets.entities.Tweet;
import com.tweets.entities.User;
import com.tweets.repository.IdiomRepository;
import com.tweets.repository.UserRepository;
import com.tweets.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TweetsDatabase implements CommandLineRunner {

    private static IdiomRepository idiomRepository;
    private static TweetService tweetService;
    private static UserRepository userRepository;

    @Autowired
    public TweetsDatabase(IdiomRepository idiomRepository, TweetService tweetService, UserRepository userRepository) {
        this.idiomRepository = idiomRepository;
        this.tweetService = tweetService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String[] args) {
        List<Idiom> idiomList = createIdioms();
        List<User> userList = createUsers();
        createTweet(idiomList, userList);
    }

    private void createTweet(List<Idiom> idiomList, List<User> userList) {
        Tweet tweet = new TweetBuilder().setId(null).setTexto("Texto Novo Tweet")
            .setValid(false).setUser(userList.get(0)).setIdiom(idiomList.get(0)).setHashtag(getHashTagList()).build();

        Tweet tweetValid = new TweetBuilder().setId(null).setTexto("Texto Novo Tweet Validated")
                .setValid(true).setUser(userList.get(1)).setIdiom(idiomList.get(1)).setHashtag(getHashTagList()).build();

        tweetService.saveTweet(tweet);
        tweetService.saveTweet(tweetValid);

        List<Tweet> tweets = (List) tweetService.getAll();

        tweets.forEach(t -> System.out.println("Tweet Saved " + t.getId() + " - " +
                " -> " + t.getTexto() + " -> valid " + t.getValid()));
    }

    private List<User> createUsers() {
        User user1 = new UserBuilder().setName("Fernando").setFollowersCount(1900l)
                .setLocation("San Francisco, CA").build();

        User user2 = new UserBuilder().setName("Henrique").setFollowersCount(2900l)
                .setLocation("San Francisco, CA").build();

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> userList = (List<User>) userRepository.findAll();

        userList.forEach(i -> System.out.println("USER SAVED " + i.getId() + " - " + i.getName()));
        return userList;
    }

    private List<Idiom> createIdioms() {
        List<String> idioms = Arrays.asList("SPANISH","FRENCH","ITALIAN");

        for(int i = 0 ; i < idioms.size() ; i ++) {
            idiomRepository.save(new IdiomBuilder().setId(null).setName(idioms.get(i)).build());
        };

        List<Idiom> idiomList = (List) idiomRepository.findAll();

        idiomList.forEach(i -> System.out.println("IDIOM SAVED " + i.getId() + " - " + i.getName()));

        return idiomList;
    }

    private List<String> getHashTagList() {
        return Arrays.asList(
            "#olimpiadas", "#olimpiadas", "#olimpiadas",
            "#olympics","#olympics","#olympics",
            "#brasil", "#brasil", "#brasil",
            "#esporte", "#esporte", "#esporte",
            "#tokyo", "#tokyo", "#tokyo",
            "#olimpSoccer", "#olimpSoccer", "#olimpSoccer",
            "#judo", "#judo", "#judo",
            "#styleHealth", "#styleHealth", "#styleHealth",
            "#olimp", "#olimp", "#olimp",
            "#judoOlimpic", "#judoOlimpic", "#judoOlimpic",
            "#style", "#style", "#style",
            "#olimpSport", "#olimpSport", "#olimpSport",
            "#judoBrasil",
            "#styleBrasil"
        );
    }
}
