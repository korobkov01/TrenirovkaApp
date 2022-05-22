package com.example.trenirovkaapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trenirovkaapp.R
import com.example.trenirovkaapp.adapters.DayModel
import com.example.trenirovkaapp.adapters.DaysAdapter
import com.example.trenirovkaapp.adapters.ExerciseAdapter
import com.example.trenirovkaapp.adapters.ExerciseModel
import com.example.trenirovkaapp.databinding.ExerciseBinding
import com.example.trenirovkaapp.databinding.ExercisesListFragmentBinding
import com.example.trenirovkaapp.databinding.FragmentDaysBinding
import com.example.trenirovkaapp.utils.FragmentManager
import com.example.trenirovkaapp.utils.MainViewModel
import com.example.trenirovkaapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable

class ExercisesFragment : Fragment() {
    private var timer: CountDownTimer? = null
    private lateinit var binding: ExerciseBinding
    private var exerciseCounter = 0
    private var exList: ArrayList<ExerciseModel>? = null
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.mutableListExercise.observe(viewLifecycleOwner){
            exList = it
            nextExercise()
        }
        binding.bNext.setOnClickListener{
            nextExercise()
        }
    }

    private fun  nextExercise(){
        if(exerciseCounter < exList?.size!!){
            val ex = exList?.get(exerciseCounter++) ?: return
            showExercise(ex)
            setExerciseType(ex)
        }
        else{
            FragmentManager.setFragment(DaysFragment.newInstance(), activity as AppCompatActivity)
        }
    }

    private fun showExercise(exercise: ExerciseModel) = with(binding){
        imMain.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
        tvName.text = exercise.name
    }

    private fun setExerciseType(exercise: ExerciseModel){
        if(exercise.time.startsWith("x")){
            binding.tvTime.text = exercise.time
        }
        else{
            startTimer(exercise)
        }
    }

    private fun startTimer(exercise: ExerciseModel) = with(binding){
        progressBar.max = exercise.time.toInt() * 1000
        timer?.cancel()
        timer = object : CountDownTimer(exercise.time.toLong() * 1000, 1){
            override fun onTick(restTime: Long) {
                tvTime.text = TimeUtils.getTime(restTime)
                progressBar.progress = restTime.toInt()
            }

            override fun onFinish() {
                nextExercise()
            }

        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        timer?.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExercisesFragment()
    }
}