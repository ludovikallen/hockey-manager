package com.hockeymanager.application.patches.models;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportPatchDto {
    @NotEmpty
    @Nonnull
    String folderPath;
}
