package com.example.databaseapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.databaseapp.databinding.FragmentEditDialogBinding

class EditDialogFragment(
    private val roomShow: RoomShow,
    private val refreshShowList: () -> Unit
    ) : DialogFragment() {

    private var _binding: FragmentEditDialogBinding? = null

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
        return FragmentEditDialogBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable=true

        with(binding) {

            showNameTxtv.text.append(roomShow.showName)
            seriesTxtv.text.append(roomShow.showSeries)

            confirmEditBtn.setOnClickListener {
                if (showNameTxtv.text.toString() != "" && seriesTxtv.text.toString() != "") {
                    val newRoomShow=RoomShow(
                        id=roomShow.id,
                        showName=showNameTxtv.text.toString(),
                        showSeries=seriesTxtv.text.toString()
                    )
                    /*
                    т.к. у нас в интерфейсе прописана стратегия на замену в случае конфликта,
                    то можно и так написать, по этому явно кидаю туда уже существующий в таблице id
                    */
                    showDao.insertShows(newRoomShow)
                    refreshShowList()
                    dismiss()
                } else {
                    handleError("Write something in fields above")
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