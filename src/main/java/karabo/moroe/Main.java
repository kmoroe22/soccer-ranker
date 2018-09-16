package karabo.moroe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        TeamRepository teamRepository = createTeamRepository();
        League league = createLeague(teamRepository);
        GameConsoleReader reader = new GameConsoleReader(teamRepository);

        System.out.println("Please enter location of input file: ");
        Scanner consoleScanner = new Scanner(System.in);

        String location = consoleScanner.nextLine();

        Scanner fileScanner = new Scanner(new File(location));

        while (fileScanner.hasNextLine()) {
            Game game = reader.create(fileScanner.nextLine());
            league.update(game);
        }
        league.rankings().forEach(a -> System.out.println(a.print()));
    }


    private static TeamRepository createTeamRepository() {
        return new TeamRepository();
    }

    private static League createLeague(TeamRepository repository) {
        return new League(repository);
    }
}
