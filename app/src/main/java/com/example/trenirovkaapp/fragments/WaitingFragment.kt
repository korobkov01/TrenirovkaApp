package com.example.trenirovkaapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trenirovkaapp.R
import com.example.trenirovkaapp.adapters.DayModel
import com.example.trenirovkaapp.adapters.DaysAdapter
import com.example.trenirovkaapp.adapters.ExerciseAdapter
import com.example.trenirovkaapp.databinding.ExercisesListFragmentBinding
import com.example.trenirovkaapp.databinding.FragmentDaysBinding
import com.example.trenirovkaapp.databinding.WaitingFragmentBinding
import com.example.trenirovkaapp.utils.FragmentManager
import com.example.trenirovkaapp.utils.MainViewModel
import com.example.trenirovkaapp.utils.TimeUtils

const val COUNT_DOWN_TIME = 11000L
class WaitingFragment : Fragment() {
    private lateinit var binding: WaitingFragmentBinding
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WaitingFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pBar.max = COUNT_DOWN_TIME.toInt()
        startTimer()
    }

    private fun startTimer() = with(binding){
        timer = object : CountDownTimer(COUNT_DOWN_TIME, 1){
            override fun onTick(restTime: Long) {
                tvTimer.text = TimeUtils.getTime(restTime)
                pBar.progress = restTime.toInt()
            }

            override fun onFinish() {
               FragmentManager.setFragment(ExercisesFragment.newInstance(), activity as AppCompatActivity)
            }

        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = WaitingFragment()
    }
}