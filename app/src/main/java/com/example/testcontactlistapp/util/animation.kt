package com.example.testcontactlistapp.util

import androidx.navigation.navOptions
import com.example.testcontactlistapp.R

val OPTIONS = navOptions {
    anim {
        enter = R.anim.fade_in
        exit = R.anim.fade_out
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }
}