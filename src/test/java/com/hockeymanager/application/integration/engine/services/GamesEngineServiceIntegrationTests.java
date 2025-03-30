package com.hockeymanager.application.integration.engine.services;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.hockeymanager.application.engine.models.GameResult;
import com.hockeymanager.application.engine.services.GamesEngineService;
import com.hockeymanager.application.patches.models.ImportPatchDto;
import com.hockeymanager.application.patches.models.PatchDto;
import com.hockeymanager.application.patches.services.PatchesService;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GamesEngineServiceIntegrationTests {
    @Autowired
    private GamesEngineService gamesEngineService;

    @Test
    void shouldImportPatchFromPersistentCsvFiles() throws IOException {
        // Arrange
        PatchesService service = new PatchesService();

        Path path = Paths.get("src/test/resources/patches-test-data");
        ImportPatchDto patch = new ImportPatchDto(path.toFile().getAbsolutePath());

        // Act
        PatchDto result = service.importPatch(patch);

        GameResult gameResult = gamesEngineService.simulateGame(result.getTeams().get(0), result.getTeams().get(1));

        assertNotEquals(gameResult.getHomeScore(), gameResult.getAwayScore());
    }
}
