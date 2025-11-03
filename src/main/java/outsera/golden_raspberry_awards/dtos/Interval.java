package outsera.golden_raspberry_awards.dtos;

  public record Interval(
    String producer,
    int interval,
    int previousWin,
    int followingWin
  ) {}
