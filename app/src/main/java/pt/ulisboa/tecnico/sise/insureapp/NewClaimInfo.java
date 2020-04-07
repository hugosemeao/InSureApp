package pt.ulisboa.tecnico.sise.insureapp;

public class NewClaimInfo {
    private final String claimTitle;
    private final String claimDate;
    private final String claimPlateInformation;
    private final String claimDescription;

    public NewClaimInfo(String title, String date, String plateInfo, String description){
        this.claimTitle = title;
        this.claimDate = date;
        this.claimPlateInformation = plateInfo;
        this.claimDescription = description;
    }

    public String getClaimTitle() {
        return claimTitle;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public String getClaimPlateInformation() {
        return claimPlateInformation;
    }

    public String getClaimDescription() {
        return claimDescription;
    }
}
