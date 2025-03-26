package com.hockeymanager.application.integration.patches.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hockeymanager.application.patches.models.ImportPatchDto;
import com.hockeymanager.application.patches.models.PatchDto;
import com.hockeymanager.application.patches.services.PatchesService;
import jakarta.validation.ValidationException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class PatchesServiceIntegrationTest {
    @Test
    void shouldImportPatchFromPersistentCsvFiles() throws IOException {
        // Arrange
        PatchesService service = new PatchesService();

        Path path = Paths.get("src/test/resources/patches-test-data");
        ImportPatchDto patch = new ImportPatchDto(path.toFile().getAbsolutePath());

        // Act
        PatchDto result = service.importPatch(patch);

        // Assert
        var nick = result.getPlayers().stream().filter(t -> t.getFirstName().startsWith("Nick")).findFirst()
                .orElseThrow();
        var torontoPlayers = result.getPlayers().stream().filter(t -> t.getTeam().getName().equals("Toronto Leaves"))
                .toList();
        var montrealGoalies = result.getGoalies().stream().filter(t -> t.getTeam().getName().equals("Montréal Habs"))
                .toList();

        assertEquals("Montréal Habs", nick.getTeam().getName());
        assertEquals(2, torontoPlayers.size());
        assertEquals(1, montrealGoalies.size());
        assertEquals(2, result.getTeams().size());
        assertEquals(3, result.getPlayers().size());
        assertEquals(2, result.getGoalies().size());
    }

    @Test
    void shouldThrowExceptionIfMissingFile() {
        // Arrange
        PatchesService service = new PatchesService();
        ImportPatchDto patch = new ImportPatchDto("invalid/path");

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> service.importPatch(patch));
        assertEquals("Teams file not found!", exception.getMessage());
    }
}
