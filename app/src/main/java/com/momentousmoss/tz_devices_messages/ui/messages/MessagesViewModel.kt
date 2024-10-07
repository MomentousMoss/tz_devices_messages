package com.momentousmoss.tz_devices_messages.ui.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momentousmoss.tz_devices_messages.dto.MessageDto
import com.momentousmoss.tz_devices_messages.utils.formatDate
import com.momentousmoss.tz_devices_messages.utils.formatTime
import java.util.Calendar
import java.util.GregorianCalendar

class MessagesViewModel : ViewModel() {

    val fillReceivedMessages = MutableLiveData<List<MessageDto>?>()
    val addNewSentMessage = MutableLiveData<MessageDto>()

    val currentAuthor = "Dmitrii"

    val newMessageAuthor = MutableLiveData<String>()
    val newMessageText = MutableLiveData<String>()

    private val _showEmptyAuthorError = MutableLiveData<Boolean>()
    val showEmptyAuthorError: LiveData<Boolean> = _showEmptyAuthorError

    private val _showEmptyTextError = MutableLiveData<Boolean>()
    val showEmptyTextError: LiveData<Boolean> = _showEmptyTextError

    fun init() {
        newMessageAuthor.value = currentAuthor
        fillReceivedMessages()
    }

    private fun fillReceivedMessages() {
        val currentDateTime = (Calendar.getInstance() as GregorianCalendar).time
        fillReceivedMessages.postValue(
            mutableListOf(
                MessageDto(
                    id = 1,
                    time = formatTime(currentDateTime),
                    date = formatDate(currentDateTime),
                    author = "Dmitrii",
                    text = "Approve all livedData"
                ),
                MessageDto(
                    id = 2,
                    time = formatTime(currentDateTime),
                    date = formatDate(currentDateTime),
                    author = "Dmitrii",
                    text = "Check CAM"
                ),
                MessageDto(
                    id = 3,
                    time = formatTime(currentDateTime),
                    date = formatDate(currentDateTime),
                    author = "Anatolii",
                    text = "Check"
                )
            )
        )
    }

    fun checkSendMessage() {
        val newMessageAuthor = newMessageAuthor.value
        _showEmptyAuthorError.value = false
        if (newMessageAuthor.isNullOrEmpty()) {
            _showEmptyAuthorError.value = true
            return
        }
        val newMessageText = newMessageText.value
        _showEmptyTextError.value = false
        if (newMessageText.isNullOrEmpty()) {
            _showEmptyTextError.value = true
            return
        }
        sendNewMessage(newMessageAuthor, newMessageText)
    }

    private fun sendNewMessage(newMessageAuthor: String, newMessageText: String) {
        val currentDateTime = (Calendar.getInstance() as GregorianCalendar).time
        addNewSentMessage.postValue(
            MessageDto(
                time = formatTime(currentDateTime),
                date = formatDate(currentDateTime),
                author = newMessageAuthor,
                text = newMessageText
            )
        )
    }
}