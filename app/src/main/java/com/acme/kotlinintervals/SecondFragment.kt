package com.acme.kotlinintervals

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.acme.kotlinintervals.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var programSelectedListener: OnProgramSelectedListener? = null

    fun setProgramSelectedListener(listener: OnProgramSelectedListener) {
        programSelectedListener = listener
        Log.i("playTAG", "listen fore")

        // link buttons
        binding.b10sfore.setOnClickListener{
            listener.playProgramSelected(10)
            binding.minutes.text = listener.getTotalTime(10).toString()
        }
        binding.b5sfore.setOnClickListener{
            listener.playProgramSelected(5)
            binding.minutes.text = listener.getTotalTime(5).toString()
        }
        binding.b8sfore.setOnClickListener{
            listener.playProgramSelected(8)
            binding.minutes.text = listener.getTotalTime(8).toString()
        }
        binding.bStop.setOnClickListener{
            listener.stopFore()
        }

        val screenToggler = listener as OnScreenToggle

        binding.bOn.setOnClickListener{
            screenToggler.setScreen(true)
        }
        binding.bOff.setOnClickListener{
            screenToggler.setScreen(false)
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setProgramSelectedListener(activity as OnProgramSelectedListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnProgramSelectedListener {
        fun playProgramSelected(program: Int)
        fun getTotalTime(program: Int) : Int
        fun stopFore()
    }

    interface OnScreenToggle {
        fun setScreen(on: Boolean)
    }
}