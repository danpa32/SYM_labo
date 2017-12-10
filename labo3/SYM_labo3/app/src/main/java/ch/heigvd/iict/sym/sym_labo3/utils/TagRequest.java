package ch.heigvd.iict.sym.sym_labo3.utils;

import android.nfc.Tag;

import ch.heigvd.iict.sym.sym_labo3.utils.listener.ICommunicationEventListener;

/**
 * Created by daniel on 08.12.17.
 */

public class TagRequest {
        private Tag tag;
        private ICommunicationEventListener listener;

        public TagRequest(Tag tag, ICommunicationEventListener listener) {
            this.tag = tag;
            this.listener = listener;
        }

        public Tag getTag() {
            return tag;
        }

        public void setTag(Tag tag) {
            this.tag = tag;
        }

        public ICommunicationEventListener getListener() {
            return listener;
        }

        public void setListener(ICommunicationEventListener listener) {
            this.listener = listener;
        }
}
