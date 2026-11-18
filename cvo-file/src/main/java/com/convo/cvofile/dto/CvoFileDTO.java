package com.convo.cvofile.dto;

import com.convo.cvofile.entity.CvoFile;

public record CvoFileDTO(Long id, String name, String description, String uuid) {

    static public CvoFileDTO from(CvoFile cvoFile) {
        return new CvoFileDTO(cvoFile.getId(), cvoFile.getName(), cvoFile.getDescription(), cvoFile.getUuid());
    }

    // uuid field excluded on purpose
    public CvoFile toEntity() {
        CvoFile cvoFile = new CvoFile();
        cvoFile.setName(this.name);
        cvoFile.setDescription(this.description);
        cvoFile.setUuid(this.uuid);
        return cvoFile;
    }

    public void setU(String uuid) {
    }
}
