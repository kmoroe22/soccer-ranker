package karabo.moroe;

public class Rank {

    private long rank;

    private String team;

    private long points;

    public Rank(int rank, String team, long points) {
        this.rank = rank;
        this.team = team;
        this.points = points;
    }

    protected String getTeam() {
        return team;
    }

    protected long getRank() {
        return rank;
    }

    public long getPoints() {
        return points;
    }

    public String print() {
        return String.format("%d. %s, %d pts", rank, team, points);
    }

}
