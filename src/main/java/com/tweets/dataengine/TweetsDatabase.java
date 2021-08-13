package com.tweets.dataengine;

import com.tweets.builder.IdiomBuilder;
import com.tweets.builder.TweetBuilder;
import com.tweets.builder.UserBuilder;
import com.tweets.entities.Idiom;
import com.tweets.entities.Tweet;
import com.tweets.entities.User;
import com.tweets.repository.IdiomRepository;
import com.tweets.repository.TweetRepository;
import com.tweets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TweetsDatabase implements CommandLineRunner {

    private static IdiomRepository idiomRepository;
    private static TweetRepository tweetRepository;
    private static UserRepository userRepository;

    @Autowired
    public TweetsDatabase(IdiomRepository idiomRepository, TweetRepository tweetRepository, UserRepository userRepository) {
        this.idiomRepository = idiomRepository;
        this.tweetRepository = tweetRepository;
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
            .setValid(false).setUser(userList.get(0)).setIdiom(idiomList.get(0)).build();

        Tweet tweetValid = new TweetBuilder().setId(null).setTexto("Texto Novo Tweet Validated")
                .setValid(false).setUser(userList.get(1)).setIdiom(idiomList.get(1)).build();

        tweetRepository.save(tweet);
        tweetRepository.save(tweetValid);

        List<Tweet> tweets = (List) tweetRepository.findAll();

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
}
