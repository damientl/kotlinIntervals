package com.acme.kotlinintervals

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.acme.kotlinintervals.databinding.FragmentFirstBinding
import com.acme.kotlinintervals.intervals.Interactor

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var programSelectedListener: OnProgramSelectedListener? = null

    fun setProgramSelectedListener(listener: OnProgramSelectedListener) {
        this.programSelectedListener = listener


        Log.i("playTAG", "listemenr")

        binding.b10s.setOnClickListener{
            programSelectedListener!!.onProgramSelected(10)
        }
        binding.b5s.setOnClickListener {
            programSelectedListener!!.onProgramSelected(5)
        }
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        setProgramSelectedListener(activity as OnProgramSelectedListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnProgramSelectedListener {
        fun onProgramSelected(program: Int)
    }
}