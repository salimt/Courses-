import '../imports/api/musicMachine.js';

// MusicMachine.remove({});
if (MusicMachine.find().count() === 0) {
  MusicMachine.insert({slide: 50, startdac: 0, drums: 1, bassline: 1, arp: 1, cymbal: 1, snaredrum: 1});
}
