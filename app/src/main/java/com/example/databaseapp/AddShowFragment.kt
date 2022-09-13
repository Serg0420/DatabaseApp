package com.example.databaseapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.databaseapp.databinding.FragmentAddShowBinding

class AddShowFragment : Fragment() {

    private var _binding: FragmentAddShowBinding? = null

    private val showDao by lazy {
        requireContext().showDatabase.showDao()
    }

    private val binding
        get() = requireNotNull(_binding) {
            handleError("View was destroyed")
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAddShowBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            addShowBtn.setOnClickListener {
                if (showNameTxtv.text.toString() != "" && seriesTxtv.text.toString() != "") {

                    AlertDialog.Builder(requireContext())
                        .setCancelable(true)
                        .setMessage(getString(R.string.add_message))
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            showDao.insertShows(
                                RoomShow(
                                    showName = showNameTxtv.text.toString(),
                                    showSeries = seriesTxtv.text.toString()
                                )
                            )
                            showNameTxtv.text.clear()
                            seriesTxtv.text.clear()
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ -> }
                        .show()

                } else {
                    handleError(getString(R.string.error_add_message))
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleError(errorTxt: String) {
        Toast.makeText(requireContext(), errorTxt, Toast.LENGTH_SHORT).show()
    }

}