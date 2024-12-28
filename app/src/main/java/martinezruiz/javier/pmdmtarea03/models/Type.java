package martinezruiz.javier.pmdmtarea03.models;

import com.google.gson.annotations.SerializedName;

public record Type (@SerializedName("name") String name, String url){ }
