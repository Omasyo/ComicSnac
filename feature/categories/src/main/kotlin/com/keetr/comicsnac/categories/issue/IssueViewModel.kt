package com.keetr.comicsnac.categories.issue

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.data.issue.IssueRepository
import com.keetr.comicsnac.model.issue.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class IssueViewModel @Inject constructor(
    issuesRepository: IssueRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Issue>(settingsRepository) {

    override val items =
        issuesRepository.getAllIssues().cachedIn(viewModelScope)

}

