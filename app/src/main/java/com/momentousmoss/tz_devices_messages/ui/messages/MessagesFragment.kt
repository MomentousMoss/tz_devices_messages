package com.momentousmoss.tz_devices_messages.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.momentousmoss.tz_devices_messages.R
import com.momentousmoss.tz_devices_messages.databinding.FragmentMessagesBinding
import com.momentousmoss.tz_devices_messages.dto.MessageDto
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagesFragment : Fragment() {

    private val messagesViewModel by viewModel<MessagesViewModel>()

    private val messagesListReceived: MutableList<MessageDto?> = mutableListOf()
    private val messagesListSent: MutableList<MessageDto?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messagesViewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMessagesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_messages,
            null,
            false
        )
        binding.apply {
            viewModel = messagesViewModel
        }
        messagesViewModel.apply {
            showEmptyAuthorError.observe(viewLifecycleOwner) {
                binding.newMessageAuthor.isErrorEnabled = it
                if (it) {
                    binding.newMessageAuthor.error = getString(R.string.message_send_new_error)
                }
            }
            showEmptyTextError.observe(viewLifecycleOwner) {
                binding.newMessageText.isErrorEnabled = it
                if (it) {
                    binding.newMessageText.error = getString(R.string.message_send_new_error)
                }
            }
            fillReceivedMessages.observe(viewLifecycleOwner) { messagesData ->
                fillReceivedMessages(
                    binding.messagesListReceived,
                    messagesData
                )
            }
            addNewSentMessage.observe(viewLifecycleOwner) { messageData ->
                addNewSentMessage(
                    binding.messagesListSent,
                    messageData
                )
            }
        }
        return binding.root
    }

    private fun fillReceivedMessages(
        messagesRecyclerView: RecyclerView,
        messagesData: List<MessageDto>?
    ) {
        messagesRecyclerView.apply {
            adapter = MessagesAdapter(messagesListReceived)
            messagesListReceived.clear()
            messagesData?.forEach { messageData ->
                messagesListReceived.add(messageData)
            }
            adapter?.notifyItemRangeChanged(0, adapter?.itemCount ?: 0)
        }
    }

    private fun addNewSentMessage(
        messagesRecyclerView: RecyclerView,
        messageData: MessageDto
    ) {
        messagesRecyclerView.apply {
            adapter = MessagesAdapter(messagesListSent)
            messagesListSent.add(0, messageData.apply { id = messagesListSent.size })
        }
    }

}