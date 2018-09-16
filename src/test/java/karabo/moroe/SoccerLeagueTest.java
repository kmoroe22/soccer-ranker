package karabo.moroe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static junit.framework.TestCase.fail;

public class SoccerLeagueTest {

    private TeamRepository repository;
    private League league;

    @Before
    public void setUp() {
        repository = new TeamRepository();
        league = new League(repository);
    }

    @Test
    public void whenTeamWinsThen3PointsAreAddedForTeam() {
        Team teamA = repository.findOrCreate("Team A");
        Team teamB = repository.findOrCreate("Team B");

        Game game1 = new Game(teamA, 2, teamB, 1);

        league.update(game1);

        List<Rank> rankings = league.rankings();
        Assert.assertEquals(rankings.get(0).getTeam(), "Team A");
        Assert.assertEquals(rankings.get(0).getPoints(), 3);
        Assert.assertEquals(rankings.get(0).getRank(), 1);

        Assert.assertEquals(rankings.get(1).getTeam(), "Team B");
        Assert.assertEquals(rankings.get(1).getPoints(), 0);
        Assert.assertEquals(rankings.get(1).getRank(), 2);
    }

    @Test
    public void whenTeamDrawsThen1PointsAreAddedForBothTeams() {
        Team teamA = repository.findOrCreate("Team A");
        Team teamB = repository.findOrCreate("Team B");

        Game game1 = new Game(teamA, 1, teamB, 1);

        league.update(game1);

        List<Rank> rankings = league.rankings();
        Assert.assertEquals(rankings.get(0).getTeam(), "Team A");
        Assert.assertEquals(rankings.get(0).getPoints(), 1);
        Assert.assertEquals(rankings.get(0).getRank(), 1);

        Assert.assertEquals(rankings.get(1).getTeam(), "Team B");
        Assert.assertEquals(rankings.get(1).getPoints(), 1);
        Assert.assertEquals(rankings.get(1).getRank(), 1);
    }

    @Test
    public void whenTeamCHas4DrawsAndTeamBHas1WinThenTeamCHas4PointsAndTeamBHas3() {
        Team teamA = repository.findOrCreate("Team A");
        Team teamB = repository.findOrCreate("Team B");
        Team teamC = repository.findOrCreate("Team C");
        Team teamD = repository.findOrCreate("Team D");

        Game game1 = new Game(teamA, 1, teamC, 1);
        Game game2 = new Game(teamB, 2, teamC, 2);
        Game game3 = new Game(teamD, 4, teamC, 4);
        Game game4 = new Game(teamA, 7, teamC, 7);
        Game game5 = new Game(teamB, 4, teamA, 0);
        Game game6 = new Game(teamD, 1, teamC, 1);
        league.update(game1);
        league.update(game2);
        league.update(game3);
        league.update(game4);
        league.update(game5);
        league.update(game6);


        List<Rank> rankings = league.rankings();
        Assert.assertEquals("Team C", rankings.get(0).getTeam());
        Assert.assertEquals("Team B", rankings.get(1).getTeam());
        Assert.assertEquals("Team A", rankings.get(2).getTeam());
        Assert.assertEquals("Team D", rankings.get(3).getTeam());

        Assert.assertEquals(5, rankings.get(0).getPoints());
        Assert.assertEquals(4, rankings.get(1).getPoints());
        Assert.assertEquals(2, rankings.get(2).getPoints());
        Assert.assertEquals(2, rankings.get(3).getPoints());

        Assert.assertEquals(3, rankings.get(2).getRank());
        Assert.assertEquals(3, rankings.get(3).getRank());
    }

    @Test
    public void canReadLeagueResultsFromFIle() throws FileNotFoundException {
        GameConsoleReader reader = new GameConsoleReader(repository);

        String file = getClass().getClassLoader().getResource("test_input.txt").getFile();

        Scanner scanner = new Scanner(new File(file));

        while (scanner.hasNextLine()) {
            league.update(reader.create(scanner.nextLine()));
        }

        List<Rank> rankings = league.rankings();
        Assert.assertEquals(rankings.size(), 5);

        Assert.assertEquals(rankings.get(0).getTeam(), "Tarantulas");
        Assert.assertEquals(rankings.get(0).getRank(), 1);
        Assert.assertEquals(rankings.get(0).getPoints(), 7);

        Assert.assertEquals(rankings.get(1).getTeam(), "Lions");
        Assert.assertEquals(rankings.get(1).getRank(), 2);
        Assert.assertEquals(rankings.get(1).getPoints(), 6);

        Assert.assertEquals(rankings.get(2).getTeam(), "FC Awesome");
        Assert.assertEquals(rankings.get(2).getRank(), 3);
        Assert.assertEquals(rankings.get(2).getPoints(), 5);

        Assert.assertEquals(rankings.get(3).getTeam(), "Grouches");
        Assert.assertEquals(rankings.get(3).getRank(), 3);
        Assert.assertEquals(rankings.get(3).getPoints(), 5);

        Assert.assertEquals(rankings.get(4).getTeam(), "Snakes");
        Assert.assertEquals(rankings.get(4).getRank(), 4);
        Assert.assertEquals(rankings.get(4).getPoints(), 2);
    }

    @Test
    public void whenTeamHasNegativeScoreItIsInvalid() {
        Team teamA = repository.findOrCreate("Team A");
        Team teamB = repository.findOrCreate("Team B");

        try {
            new Game(teamA, 1, teamB, -1);
            fail("Game should not allow a negative score");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals(ex.getMessage(), "Soccer scores cannot be negative");
        }
    }


}