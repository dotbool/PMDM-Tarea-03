package martinezruiz.javier.pmdmtarea03.models;

import java.util.List;

public record PokedexList(int count, String next, String previous, List<PokedexItem> results){}