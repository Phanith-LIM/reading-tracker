package com.app.readingtracker.pages
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PageViewModel : ViewModel() {
    private val _selectedIndex = MutableStateFlow<Int>(0);
    val selectedIndex: StateFlow<Int> = _selectedIndex.asStateFlow();

    fun setSelectedIndex(index: Int) {
        if(index == _selectedIndex.value) {
            return;
        }
           _selectedIndex.value = index;

    }
}
