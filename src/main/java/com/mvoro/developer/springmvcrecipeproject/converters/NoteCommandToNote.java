package com.mvoro.developer.springmvcrecipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mvoro.developer.springmvcrecipeproject.commands.NoteCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Note;

import lombok.Synchronized;

@Component
public class NoteCommandToNote implements Converter<NoteCommand, Note> {

    @Synchronized
    @Override
    public Note convert(NoteCommand source) {
        if (source == null) {
            return null;
        }

        Note note = new Note();
        note.setId(source.getId());
        note.setNote(source.getNote());

        return note;
    }
}
