package karabo.moroe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameConsoleReader {

    private TeamRepository repository;

    public GameConsoleReader(TeamRepository repository) {
        this.repository = repository;
    }

    public Game create(String input) {

        Pattern compile = Pattern.compile("([A-Za-z ]*){1} ([0-9]).*, ([A-Za-z ]*){1} ([0-9]).*");
        Matcher matcher = compile.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Input not valid: " +input);
        }
        String homeTeamName = matcher.group(1);
        String homeTeamScore  = matcher.group(2);
        String awayTeamName  = matcher.group(3);
        String awayTeamScore  = matcher.group(4);

        Team homeTeam = repository.findOrCreate(homeTeamName);
        Team awayTeam = repository.findOrCreate(awayTeamName);
        return new Game(homeTeam, Integer.parseInt(homeTeamScore), awayTeam, Integer.parseInt(awayTeamScore));
    }


}
