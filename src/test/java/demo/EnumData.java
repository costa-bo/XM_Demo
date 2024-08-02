package demo;

public interface EnumData {
    enum SliderChronicleOptions {
        RecentNext("Recent & Next"),
        Today("Today"),
        Tomorrow("Tomorrow"),
        ThisWeek("This Week"),
        NextWeek("Next Week");

        public final String label;

        SliderChronicleOptions(String label){
            this.label = label;
        }
    }
}
