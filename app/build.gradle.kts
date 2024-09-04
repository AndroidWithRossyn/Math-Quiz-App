plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseCrashlytics)
}

android {
    namespace = "com.satyamltd.braintest.mathquiz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.satyamltd.braintest.mathquiz"
        minSdk = 24
        targetSdk = 34
        versionCode = 5
        versionName = "2.5.0.18"
        setProperty("archivesBaseName", "Math_Quiz-$versionName")
        vectorDrawables.useSupportLibrary = true
        renderscriptTargetApi = 24
        renderscriptSupportModeEnabled = true
        multiDexEnabled = true
        resourceConfigurations += setOf()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        jniLibs.useLegacyPackaging = false
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }

    allprojects {
        gradle.projectsEvaluated {
            tasks.withType<JavaCompile> {
                options.compilerArgs.add("-Xlint:unchecked")
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.play.services.ads)

    implementation (libs.play.services.games)
    implementation (libs.play.services.auth)
    implementation (libs.play.services.analytics)


    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.config)
    implementation(libs.firebase.inappmessaging.display)
    implementation(libs.firebase.messaging)
}