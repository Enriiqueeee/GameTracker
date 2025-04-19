package edu.iesam.gametracker.features.setting.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import edu.iesam.gametracker.R
import edu.iesam.gametracker.databinding.FragmentSettingBinding
import androidx.core.net.toUri

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            TitleDevelopers.text = requireContext().getString(R.string.desarrolladores)

            TitleResources.text = requireContext().getString(R.string.recursos)

            TitleContact.text = requireContext().getString(R.string.email)

            ContactMail.text = requireContext().getString(R.string.mail)
            ContactMail.apply {
                setText(R.string.mail)
                setOnClickListener { openEmailClient() }
            }

            AppVersion.text = getString(
                R.string.version,
                getAppVersion()
            )
        }

    }

    private fun getAppVersion(): String =
        requireContext().packageManager
            .getPackageInfo(requireContext().packageName, 0)
            .versionName.toString()


    private fun openEmailClient() {
        val email = getString(R.string.mail)
        val intent = Intent(Intent.ACTION_SENDTO, "mailto:$email".toUri())
            .putExtra(Intent.EXTRA_SUBJECT, "Contacto desde GameTracker")

        if (intent.resolveActivity(requireContext().packageManager) != null)
            startActivity(intent)
        else
            Toast.makeText(
                requireContext(),
                "No hay cliente de correo instalado",
                Toast.LENGTH_SHORT
            ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
