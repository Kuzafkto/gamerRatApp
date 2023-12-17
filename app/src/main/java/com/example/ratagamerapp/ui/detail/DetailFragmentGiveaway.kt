package com.example.ratagamerapp.ui.detail
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.imageLoader
import coil.request.ImageRequest
import com.example.ratagamerapp.R
import com.example.ratagamerapp.data.db.CommentEntity
import com.example.ratagamerapp.databinding.FragmentDetailGiveawayBinding
import com.example.ratagamerapp.ui.list.Comments.AdapterCommentsList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragmentGiveaway : Fragment() {
    //el Fragment es el último punto en la estructura

    //usa navArgs para obtener el Id del item al que se le hizo click
    private val args: DetailFragmentGiveawayArgs by navArgs()
    //binding para vincularlo con el template
    private lateinit var binding: FragmentDetailGiveawayBinding
    //viewModel para vincularlo con el viewmodel
    private val viewModel: ViewModelDetailGiveaway by viewModels()

    //implementamos el onCreateView de siempre donde implementamos el inflado y el binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailGiveawayBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    //ahora cuando la vista está creada vamos a dentro de una cortina llamar a la funcion de buscar del viewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var nametoShare=""

        //seteamos el adapter para el recyclerView
        val rv=binding.commentList
        val layoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager

        //configurar el adapter
        val adapter = AdapterCommentsList(requireContext())
        rv.adapter=adapter
        //seteamos el adapter para el recyclerView

        //ahora nos encargamos de hacer aca setear los datos del detail y obtener los comentarios
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    viewModel.fetchGiveawayDetail(args.Id)
                    Log.d("test offline id",args.Id.toString())

                    // Actualizamos el viewModel con uiState.collect
                    viewModel.uiState.collect { giveawayDetail ->
                        val thumbnailRequest = ImageRequest.Builder(view.context)
                            .data(giveawayDetail.image)
                            .crossfade(true)
                            .target(binding.detailViewCover)
                            .build()
                        view.context.imageLoader.enqueue(thumbnailRequest)
                        binding.detailTitle.text = giveawayDetail.title
                        Log.d("test offline text",giveawayDetail.title)
                        binding.detailDescription.text = giveawayDetail.description
                        nametoShare=giveawayDetail.title
                    }
                } catch (e: Exception) {
                    Log.e("Detail ", "Error cargando: ${e.message}")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    viewModel.setComments(args.Id)
                    // Actualizamos el adapter con commentsUiState.collect
                    viewModel.commentsUiState.collect { comments ->
                        adapter.submitList(comments)
                    }
                } catch (e: Exception) {
                    // Manejar la excepción, por ejemplo, mostrar un mensaje de error.
                    Log.e("Error", "Error cargando comentarios: ${e.message}")
                    // Puedes mostrar un mensaje de error en la UI o cargar una lista de comentarios vacía.
                }
            }
        }



        //aca simplemente configuramos un boton para crear el comentario

        binding.commentButton.setOnClickListener {
            // Creamos un inflater para inflar el diseño personalizado
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_add_comment, null)

            // Referenciamos los campos de entrada del diseño personalizado
            val commentDescriptionEditText = dialogView.findViewById<EditText>(R.id.commentDescriptionEditText)
            val userEditText = dialogView.findViewById<EditText>(R.id.userEditText)

            // Creamos el cuadro de diálogo con el diseño personalizado
            val builder = AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.create_comment))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.create_comment)) { _, _ ->
                    val commentDescription = commentDescriptionEditText.text.toString()
                    val anotherFieldText = userEditText.text.toString()

                    val newComment = CommentEntity(
                        0,
                        args.Id,
                        commentDescription,
                        anotherFieldText
                    )

                    viewModel.viewModelScope.launch {
                        viewModel.addComment(newComment)
                    }
                }

            // Mostramos el cuadro de diálogo
            builder.create().show()
        }


        //boton de compartir
        val shareButton =binding.shareButton

        shareButton.setOnClickListener {
            // Texto que deseas compartir
            val shareText = getString(R.string.share_template) + ":\n"+nametoShare

            // Crea un intent implícito para compartir texto
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)

            // Inicia el intent
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
        }
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.giveawaysListFragment, false)
        }

    }
}
