package com.example.hebam.notekeeper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.preference.ListPreference;
//import android.preference.Preference;
//import android.preference.PreferenceActivity;

import androidx.appcompat.app.ActionBar;

//import androidx.preference.PreferenceFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
//import android.preference.PreferenceFragment;
//import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.core.app.NavUtils;

import java.util.List;

/**
 * A {@link } that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;


    /**
    * A preference value change listener that updates the preference's summary
    * to reflect its new value.
    */
    private static Preference.OnPreferenceChangeListener  sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener () {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        setupActionBar();

        // Because the fragment has been added to the FrameLayout
        // container at runtime—instead of defining it in the
        // activity's layout with a <fragment> element—the activity
        // can remove the fragment and replace it with a different one.

        // Add the fragment to the 'pre' LinearLayout
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.pre, new MySettingsFragment())
                .commit();

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    public static class MySettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.pref_headers, rootKey);
        }
    }

    /**
     *
     * @param caller  The fragment requesting navigation
     * @param pref The preference requesting the fragment
     * @return 	true if the fragment creation has been handled
     */
    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        // Instantiate the new Fragment
        final Bundle args = pref.getExtras();
        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
                getClassLoader(),
                pref.getFragment());
        fragment.setArguments(args);
        fragment.setTargetFragment(caller, 0);
        // Replace the existing Fragment with the new Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pre, fragment)
                .addToBackStack(null)
                .commit();
        return true;
    }

    /**
     //     * This fragment shows general preferences only. It is used when the
     //     * activity is showing a two-pane settings UI.
     //     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//            super.onCreate(savedInstanceState);
            setPreferencesFromResource(R.xml.pref_general, rootKey);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("user_display_name"));
            bindPreferenceSummaryToValue(findPreference("user_email_address"));
            bindPreferenceSummaryToValue(findPreference("user_favorite_social"));

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

//        private void bindPreferenceSummaryToValue(Preference preference) {
//            preference.setOnPreferenceChangeListener(this);
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
//            String preferenceString = preferences.getString(preference.getKey(), "");
//            onPreferenceChange(preference, preferenceString);
//        }
    }


    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DataSyncPreferenceFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//            super.onCreate(savedInstanceState);
            setPreferencesFromResource(R.xml.pref_data_sync, rootKey);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
//            bindPreferenceSummaryToValue(findPreference("sync_frequency"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
          * Binds a preference's summary to its value. More specifically, when the
          * preference's value is changed, its summary (line of text below the
          * preference title) is updated to reflect the value. The summary is also
          * immediately updated upon calling this method. The exact display format is
          * dependent on the type of preference.
          *
          * @see #sBindPreferenceSummaryToValueListener
          */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }







//        implements
//        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback{
//
//    /**
//     * A preference value change listener that updates the preference's summary
//     * to reflect its new value.
//     */
//    private static Preference.OnPreferenceChangeListener  sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener () {
//        @Override
//        public boolean onPreferenceChange(Preference preference, Object value) {
//            String stringValue = value.toString();
//
//            if (preference instanceof ListPreference) {
//                // For list preferences, look up the correct display value in
//                // the preference's 'entries' list.
//                ListPreference listPreference = (ListPreference) preference;
//                int index = listPreference.findIndexOfValue(stringValue);
//
//                // Set the summary to reflect the new value.
//                preference.setSummary(
//                        index >= 0
//                                ? listPreference.getEntries()[index]
//                                : null);
//
////            } else if (preference instanceof RingtonePreference) {
////                // For ringtone preferences, look up the correct display value
////                // using RingtoneManager.
////                if (TextUtils.isEmpty(stringValue)) {
////                    // Empty values correspond to 'silent' (no ringtone).
////                    preference.setSummary(R.string.pref_ringtone_silent);
////
////                } else {
////                    Ringtone ringtone = RingtoneManager.getRingtone(
////                            preference.getContext(), Uri.parse(stringValue));
////
////                    if (ringtone == null) {
////                        // Clear the summary if there was a lookup error.
////                        preference.setSummary(null);
////                    } else {
////                        // Set the summary to reflect the new ringtone display
////                        // name.
////                        String name = ringtone.getTitle(preference.getContext());
////                        preference.setSummary(name);
////                    }
////                }
//
//            } else {
//                // For all other preferences, set the summary to the value's
//                // simple string representation.
//                preference.setSummary(stringValue);
//            }
//            return true;
//        }
//    };
//
//
//
//    /**
//     * Helper method to determine if the device has an extra-large screen. For
//     * example, 10" tablets are extra-large.
//     */
//    private static boolean isXLargeTablet(Context context) {
//        return (context.getResources().getConfiguration().screenLayout
//                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
//    }
//
//    /**
//     * Binds a preference's summary to its value. More specifically, when the
//     * preference's value is changed, its summary (line of text below the
//     * preference title) is updated to reflect the value. The summary is also
//     * immediately updated upon calling this method. The exact display format is
//     * dependent on the type of preference.
//     *
//     * @see #sBindPreferenceSummaryToValueListener
//     */
//    private static void bindPreferenceSummaryToValue(Preference preference) {
//        // Set the listener to watch for value changes.
//        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
//
//        // Trigger the listener immediately with the preference's
//        // current value.
//        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
//                PreferenceManager
//                        .getDefaultSharedPreferences(preference.getContext())
//                        .getString(preference.getKey(), ""));
//    }
//
//    @Override
//    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
//        // Instantiate the new Fragment
//        final Bundle args = pref.getExtras();
//        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
//                getClassLoader(),
//                pref.getFragment());
//        fragment.setArguments(args);
//        fragment.setTargetFragment(caller, 0);
//        // Replace the existing Fragment with the new Fragment
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.settings_container, fragment)
//                .addToBackStack(null)
//                .commit();
//        return true;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setupActionBar();
//    }
//
//    /**
//     * Set up the {@link android.app.ActionBar}, if the API is available.
//     */
//    private void setupActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            // Show the Up button in the action bar.
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    @Override
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            if (!super.onMenuItemSelected(featureId, item)) {
//                NavUtils.navigateUpFromSameTask(this);
//            }
//            return true;
//        }
//        return super.onMenuItemSelected(featureId, item);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public boolean onIsMultiPane() {
//        return isXLargeTablet(this);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public void onBuildHeaders(List<Header> target) {
//        loadHeadersFromResource(R.xml.pref_headers, target);
//    }
//
//    /**
//     * This method stops fragment injection in malicious applications.
//     * Make sure to deny any unknown fragments here.
//     */
//    protected boolean isValidFragment(String fragmentName) {
//        return PreferenceFragmentCompat.class.getName().equals(fragmentName)
//                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
//                || DataSyncPreferenceFragment.class.getName().equals(fragmentName)
//                || NotificationPreferenceFragment.class.getName().equals(fragmentName);
//    }
//
//    /**
//     * This fragment shows general preferences only. It is used when the
//     * activity is showing a two-pane settings UI.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public static class GeneralPreferenceFragment extends PreferenceFragmentCompat {
//        @Override
//        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
////            super.onCreate(savedInstanceState);
//            setPreferencesFromResource(R.xml.pref_general, rootKey);
//            setHasOptionsMenu(true);
//
//            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
//            // to their values. When their values change, their summaries are
//            // updated to reflect the new value, per the Android Design
//            // guidelines.
//            bindPreferenceSummaryToValue(findPreference("example_text"));
//            bindPreferenceSummaryToValue(findPreference("example_list"));
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            int id = item.getItemId();
//            if (id == android.R.id.home) {
//                startActivity(new Intent(getActivity(), SettingsActivity.class));
//                return true;
//            }
//            return super.onOptionsItemSelected(item);
//        }
//    }
//
//    /**
//     * This fragment shows notification preferences only. It is used when the
//     * activity is showing a two-pane settings UI.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public static class NotificationPreferenceFragment extends PreferenceFragmentCompat {
//        @Override
//        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
////            super.onCreate(savedInstanceState);
//            setPreferencesFromResource(R.xml.pref_notification, rootKey);
//            setHasOptionsMenu(true);
//
//            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
//            // to their values. When their values change, their summaries are
//            // updated to reflect the new value, per the Android Design
//            // guidelines.
//            bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            int id = item.getItemId();
//            if (id == android.R.id.home) {
//                startActivity(new Intent(getActivity(), SettingsActivity.class));
//                return true;
//            }
//            return super.onOptionsItemSelected(item);
//        }
//    }
//
//    /**
//     * This fragment shows data and sync preferences only. It is used when the
//     * activity is showing a two-pane settings UI.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public static class DataSyncPreferenceFragment extends PreferenceFragmentCompat {
//        @Override
//        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
////            super.onCreate(savedInstanceState);
//            setPreferencesFromResource(R.xml.pref_data_sync, rootKey);
//            setHasOptionsMenu(true);
//
//            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
//            // to their values. When their values change, their summaries are
//            // updated to reflect the new value, per the Android Design
//            // guidelines.
//            bindPreferenceSummaryToValue(findPreference("sync_frequency"));
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            int id = item.getItemId();
//            if (id == android.R.id.home) {
//                startActivity(new Intent(getActivity(), SettingsActivity.class));
//                return true;
//            }
//            return super.onOptionsItemSelected(item);
//        }
//    }
}
