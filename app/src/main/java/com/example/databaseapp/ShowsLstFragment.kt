package com.example.databaseapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.databaseapp.databinding.FragmentShowsLstBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

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
            //добавил разделитель
            showsLst.addItemDecoration(
                MaterialDividerItemDecoration(
                    requireContext(), MaterialDividerItemDecoration.VERTICAL
                )
            )

            //по этому списку будем фильтровать в тулбаре
            val roomShowLst= mutableListOf<RoomShow>()
            roomShowLst.addAll(showDao.getAllShows())

            toolbar
                .menu
                .findItem(R.id.action_search)
                .actionView
                .let { it as SearchView }
                .setOnQueryTextListener(
                    object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(searchedTxt: String): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(searchedTxt: String): Boolean {
                            adapter.submitList(
                                /*
                                тут, наверно, лучше использовать фильтр по заранее созданному
                                списку как я сделал, в который один раз приходят данные из бд чем
                                использовать запрос как ниже закомментирован
                                */
                                roomShowLst.filter {
                                    it.showName.contains(searchedTxt)
                                }
                                //showDao.getAllSearchedByName(searchedTxt)
                            )
                            return true
                        }
                    }
                )

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
            .setMessage(getString(R.string.delete_message))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                showDao.deleteShow(roomShow)
                refreshShowList()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .show()
    }

    private fun editShow(roomShow: RoomShow) {
        val editDialogFragment = EditDialogFragment(roomShow) {
            refreshShowList()
        }
        editDialogFragment.show(childFragmentManager, null)
    }

    private fun refreshShowList() {
        adapter.submitList(showDao.getAllShows())
    }

    private fun handleError(errorTxt: String) {
        Toast.makeText(requireContext(), errorTxt, Toast.LENGTH_SHORT).show()
    }

}