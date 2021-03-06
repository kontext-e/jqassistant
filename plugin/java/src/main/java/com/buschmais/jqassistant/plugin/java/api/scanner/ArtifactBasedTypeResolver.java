package com.buschmais.jqassistant.plugin.java.api.scanner;

import java.util.HashMap;
import java.util.Map;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.java.api.model.JavaArtifactFileDescriptor;
import com.buschmais.jqassistant.plugin.java.api.model.TypeDescriptor;

/**
 * A type resolver considering an artifact and its optional dependencies as
 * scopes.
 */
class ArtifactBasedTypeResolver extends AbstractTypeResolver {

    private JavaArtifactFileDescriptor artifact;

    private Map<String, TypeDescriptor> artifactTypes = new HashMap<>();

    private boolean hasDependencies;

    /**
     * Constructor.
     * 
     * @param artifact
     *            The artifact which defines the scope for resolving types.
     */
    ArtifactBasedTypeResolver(JavaArtifactFileDescriptor artifact) {
        this.artifact = artifact;
        hasDependencies = artifact.getNumberOfDependencies() > 0;
        for (FileDescriptor fileDescriptor : artifact.getContains()) {
            if (fileDescriptor instanceof TypeDescriptor) {
                TypeDescriptor typeDescriptor = (TypeDescriptor) fileDescriptor;
                artifactTypes.put(typeDescriptor.getFullQualifiedName(), typeDescriptor);
            }
        }
        for (TypeDescriptor typeDescriptor : artifact.getRequiresTypes()) {
            this.artifactTypes.put(typeDescriptor.getFullQualifiedName(), typeDescriptor);
        }
    }

    @Override
    protected TypeDescriptor findInArtifact(String fullQualifiedName, ScannerContext context) {
        return artifactTypes.get(fullQualifiedName);
    }

    @Override
    protected TypeDescriptor findInDependencies(String fullQualifiedName, ScannerContext context) {
        return hasDependencies ? artifact.resolveRequiredType(fullQualifiedName) : null;
    }

    @Override
    protected void addContainedType(String fqn, TypeDescriptor typeDescriptor) {
        artifactTypes.put(fqn, typeDescriptor);
    }

    @Override
    protected void addRequiredType(String fqn, TypeDescriptor typeDescriptor) {
        artifactTypes.put(fqn, typeDescriptor);
        typeDescriptor.setRequiredBy(artifact);
    }

    @Override
    protected <T extends TypeDescriptor> void removeRequiredType(String fqn, T typeDescriptor) {
        typeDescriptor.setRequiredBy(null);
    }
}
