package com.example.databaseapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.databaseapp.databinding.FragmentShowsLstBinding

class ShowsLstFragment:Fragment() {

    private var _binding: FragmentShowsLstBinding? = null
    private val adapter by lazy {
        ShowsAdapter(requireContext())
    }
    private val showDao by lazy {
        requireContext().showDatabase.showDao()
    }

    private val binding
        get() = requireNotNull(_binding) {
         //   handleError("View was destroyed")
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentShowsLstBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            showsLst.adapter = adapter

            showDao.insertShows(RoomShow(showName = "gggg", showSeries = "dd2"))

            adapter.submitList(showDao.getAllShows())

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}