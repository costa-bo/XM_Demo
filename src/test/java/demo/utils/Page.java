package demo.utils;

import demo.pages.economicCal.EconomicCal;
import demo.pages.educationalVid.EducationalVid;
import demo.pages.home.Home;
import demo.services.Rest;

public class Page {
    public Home home = new Home();
    public EconomicCal economicCal = new EconomicCal();
    public EducationalVid educationalVid = new EducationalVid();

    public Rest rest = new Rest();
}