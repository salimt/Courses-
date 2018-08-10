package sound;

/*
 * @(#)MidiSynth.java	1.15	99/12/03
 *
 * Copyright (c) 1999 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */


import javax.sound.midi.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Illustrates general MIDI melody instruments and MIDI controllers.
 *
 * @version @(#)MidiSynth.java	1.15 99/12/03
 * @author Brian Lichtenwalter
 *
 * NOTE TO SOFTWARE CONSTRUCTION STUDENTS:
 * Reduced & modified for Software Construction course
 */
public class MidiSynth {

    private Synthesizer synthesizer;
    private Instrument instruments[];

    private ChannelData channels[];

    // EFFECTS: return the channel at the given index
    public ChannelData getSpecialisedChannel(int index){
        return channels[index];
    }


    // MODIFIES: this
    // EFFECTS:  prepares instruments, channels for playback
    public void open() {
        getSynthesizer();
        setupInstruments();
        setupChannels();
    }

    // EFFECTS: synthesizes sound given instrument, note, and velocity
    public void play(int instrument, int note, int velocity){
        ChannelData channelData = getChannelData(instrument);
        MidiChannel midiChannel = channelData.getChannel();
        midiChannel.noteOn(note, velocity);
    }

    // EFFECTS: stops playback of the given instrument
    public void stop(int instrument, int note){
        ChannelData channelData = getChannelData(instrument);
        MidiChannel midiChannel = channelData.getChannel();
        midiChannel.noteOff(note, 0);
    }

    // MODIFIES: this
    // EFFECTS:  sets up the channels for this MidiSynth
    private void setupChannels() {
        MidiChannel midiChannels[] = synthesizer.getChannels();
        channels = new ChannelData[midiChannels.length];
        for (int i = 0; i < channels.length; i++) {
            channels[i] = new ChannelData(midiChannels[i], i);
        }
    }

    // MODIFIES: this
    // EFFECTS:  populates this with a variety of instruments
    private void setupInstruments() {
        if (getSoundBank() != null) {
            instruments = synthesizer.getDefaultSoundbank().getInstruments();
            synthesizer.loadInstrument(instruments[0]);
        }
    }

    // EFFECTS: gets the soundbank from the synthesizer
    private Soundbank getSoundBank() {
        return synthesizer.getDefaultSoundbank();
    }

    private void getSynthesizer(){
        try {
            if (synthesizer == null) {
                if ((synthesizer = MidiSystem.getSynthesizer()) == null) {
                    System.out.println("getSynthesizer() failed!");
                    return;
                }
            }
            synthesizer.open();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // EFFECTS: returns the channel associated with the given instrument, stored in HashMap
    private ChannelData getChannelData(int instrument) {
        Map<Integer, ChannelData> channelMap = new HashMap<Integer, ChannelData>();
        ChannelData channelData = channelMap.get(instrument);
        if (channelData == null) {
            channelData = getSpecialisedChannel(channelMap.size());
            MidiChannel midiChannel = channelData.getChannel();
            midiChannel.programChange(instrument);
            channelMap.put(instrument, channelData);
        }
        return channelData;
    }
}
