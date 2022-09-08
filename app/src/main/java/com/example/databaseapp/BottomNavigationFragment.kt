package com.example.databaseapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.databaseapp.databinding.FragmentBottomNavigationBinding

class BottomNavigationFragment : Fragment() {

    private var _binding: FragmentBottomNavigationBinding? = null
    private val binding get() = requireNotNull(_binding) {
        "View was destroyed"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentBottomNavigationBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val nestedController =
                (childFragmentManager.findFragmentById(R.id.page_container) as NavHostFragment)
                    .navController
            bottomNavigation.setupWithNavController(nestedController)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}