# Google Play Services Ads
-keep class com.google.android.gms.ads.** { *; }

# Google Play Services Analytics
-keep class com.google.android.gms.analytics.** { *; }

# Google Play Services Auth
-keep class com.google.android.gms.auth.** { *; }

# Google Play Services Games
-keep class com.google.android.gms.games.** { *; }

# Review KTX
-keep class com.google.android.play.core.review.** { *; }

# App Update KTX
-keep class com.google.android.play.core.appupdate.** { *; }

# Firebase BOM
-dontwarn com.google.android.gms.**
-dontwarn com.google.firebase.**
-keep class com.google.firebase.** { *; }

# Firebase Crashlytics
-keep class com.google.firebase.crashlytics.** { *; }

# Firebase Analytics
-keep class com.google.firebase.analytics.** { *; }

# Firebase Config
-keep class com.google.firebase.remoteconfig.** { *; }

# Firebase In-App Messaging Display
-keep class com.google.firebase.inappmessaging.display.** { *; }

# Firebase Messaging
-keep class com.google.firebase.messaging.** { *; }


# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.google.protobuf.java_com_google_android_gmscore_sdk_target_granule__proguard_group_gtm_N1281923064GeneratedExtensionRegistryLite$Loader