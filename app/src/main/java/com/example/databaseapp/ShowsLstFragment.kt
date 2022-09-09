package com.example.databaseapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.databaseapp.databinding.FragmentShowsLstBinding

class ShowsLstFragment : Fragment() {

    private var _binding: FragmentShowsLstBinding? = null
    private val adapter by lazy {
        ShowsAdapter(
            requireContext(),
            { roomShowDelete ->
                deleteShow(roomShowDelete)
            },
            { roomShowEdit ->
                editShow(roomShowEdit)
            }
        )
    }
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

            refreshShowList()

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun deleteShow(roomShow: RoomShow) {
        AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setMessage("Delete this show from your list?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                showDao.deleteShow(roomShow)
                refreshShowList()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .show()
    }

    private fun editShow(roomShow: RoomShow) {
        val editDialogFragment=EditDialogFragment(roomShow){
            refreshShowList()
        }
        editDialogFragment.show(childFragmentManager,null)
    }

    private fun refreshShowList(){
        adapter.submitList(showDao.getAllShows())
    }

    private fun handleError(errorTxt: String) {
        Toast.makeText(requireContext(), errorTxt, Toast.LENGTH_SHORT).show()
    }

}