package ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ForkLeft
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.data.DEFAULT_SETTINGS
import de.davidbattefeld.berlinskylarks.classes.viewmodels.SettingsViewModel
import de.davidbattefeld.berlinskylarks.global.TeamGlobals
import ui.nav.SkylarksNavDestination

@Composable
fun SettingsScreen(
        vm: SettingsViewModel = viewModel(),
        infoRoute: () -> Unit,
        privacyRoute: () -> Unit,
        legalRoute: () -> Unit,
) {
    val userPreferences by vm.userPreferencesFlow.collectAsStateWithLifecycle(initialValue = DEFAULT_SETTINGS)
    val uriHandler = LocalUriHandler.current

    var expanded by remember { mutableStateOf(false) }

    LazyColumn {
        item {
            ListItem(
                headlineContent = { Text("Selected Season") },
                supportingContent = { Text(userPreferences.season.toString()) },
                leadingContent = {
                    Icon(
                        Icons.Filled.CalendarMonth,
                        contentDescription = "season icon",
                    )
                },
                trailingContent = {
                    Box(
                        modifier = Modifier
                            .wrapContentSize(Alignment.TopStart)
                    ) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Localized description")
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            vm.possibleSeasons.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption.toString()) },
                                    onClick = {
                                        vm.updateSelectedSeason(selectionOption)
                                        expanded = false
                                    },
                                )
                            }
                        }
                    }
                }
            )
        }
        item {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp),
                text = "Information",
                style = MaterialTheme.typography.titleSmall
            )
        }
        item {
            Column {
                ListItem(
                    modifier = Modifier
                        .clickable { infoRoute() },
                    headlineContent = { Text(SkylarksNavDestination.Info.title) },
                    leadingContent = {
                        Icon(
                            SkylarksNavDestination.Info.icon,
                            contentDescription = "season icon",
                        )
                    },
                )
                HorizontalDivider()
            }
        }
        item {
            Column {
                ListItem(
                    modifier = Modifier
                        .clickable { legalRoute() },
                    headlineContent = { Text(SkylarksNavDestination.LegalNotice.title) },
                    leadingContent = {
                        Icon(
                            SkylarksNavDestination.LegalNotice.icon,
                            contentDescription = "legal icon",
                        )
                    },
                )
                HorizontalDivider()
            }
        }
        item {
            ListItem(
                modifier = Modifier
                    .clickable { privacyRoute() },
                headlineContent = { Text(SkylarksNavDestination.Privacy.title) },
                leadingContent = {
                    Icon(
                        SkylarksNavDestination.Privacy.icon,
                        contentDescription = "privacy icon",
                    )
                },
            )
        }
        item {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp),
                text = "Get in Touch",
                style = MaterialTheme.typography.titleSmall
            )
        }
        item {
            Column {
                ListItem(
                    headlineContent = {
                        ClickableText(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        textDecoration = TextDecoration.Underline,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 16.sp
                                    )
                                ) {
                                    append("Visit the team website")
                                }
                            }
                        ) {
                            uriHandler.openUri(TeamGlobals.TEAM_WEBSITE_URL)
                        }
                    },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Web,
                            contentDescription = "Localized description",
                        )
                    }
                )
                HorizontalDivider()
            }
        }
        item {
            Column {
                ListItem(
                    headlineContent = {
                        ClickableText(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        textDecoration = TextDecoration.Underline,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 16.sp
                                    )
                                ) {
                                    append("Contribute to this app")
                                }
                            }
                        ) {
                            uriHandler.openUri(TeamGlobals.PROJECT_REPO)
                        }
                    },
                    leadingContent = {
                        Icon(
                            Icons.Filled.ForkLeft,
                            contentDescription = "Localized description",
                        )
                    }
                )
                HorizontalDivider()
            }
        }
        item {
            ListItem(
                headlineContent = { ClickableText(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp
                        )
                        ) {
                            append("Contact the developer")
                        }
                    }
                ) {
                    uriHandler.openUri(TeamGlobals.CONTACT_MAILTO_LINK)
                } },
                leadingContent = {
                    Icon(
                        Icons.Filled.Mail,
                        contentDescription = "Localized description",
                    )
                }
            )
        }
    }
}

/*
@Preview(
    showBackground = true,
    widthDp = 400,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(
    showBackground = true,
    widthDp = 400
)
@Composable
fun SettingsScreenPreview() {
    BerlinSkylarksTheme {
        SettingsScreen()
    }
}
*/
