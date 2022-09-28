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

    private var programSelectedListener: OnProgramSelectedForeListener? = null


    fun setProgramSelectedListener(listener: OnProgramSelectedForeListener) {
        this.programSelectedListener = listener
        Log.i("playTAG", "listen fore")

        // link buttons
        binding.b10sfore.setOnClickListener{
            programSelectedListener!!.playForeProgramSelected(10)
        }
        binding.b5sfore.setOnClickListener{
            programSelectedListener!!.playForeProgramSelected(5)
        }
        binding.bStop.setOnClickListener{
            programSelectedListener!!.stopFore()
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

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        setProgramSelectedListener(activity as OnProgramSelectedForeListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnProgramSelectedForeListener {
        fun playForeProgramSelected(program: Int)
        fun stopFore()
    }
}