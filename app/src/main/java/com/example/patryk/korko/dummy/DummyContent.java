package com.example.patryk.korko.dummy;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    public static class DummyItem {
        public final String id;
        public final String name;
        public final String email;
        public final String subjects;
        public final String oauth;
        public final int distance;


        public DummyItem(String id, String name, String email, String subjects, String oauth, int distance) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.subjects = subjects;
            this.oauth = oauth;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
