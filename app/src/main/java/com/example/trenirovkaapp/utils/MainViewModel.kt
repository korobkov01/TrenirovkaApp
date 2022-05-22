package com.example.trenirovkaapp.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trenirovkaapp.adapters.ExerciseModel

class MainViewModel : ViewModel() {
    val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()
}