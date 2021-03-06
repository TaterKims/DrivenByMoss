// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2020
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.controller.apc.mode;

import de.mossgrabers.controller.apc.controller.APCControlSurface;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.daw.data.ITrack;


/**
 * Panorama knob mode.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class SendMode extends BaseMode
{
    private int sendIndex;


    /**
     * Constructor.
     *
     * @param surface The control surface
     * @param model The model
     * @param sendIndex The index of the send
     */
    public SendMode (final APCControlSurface surface, final IModel model, final int sendIndex)
    {
        super ("Send", surface, model, APCControlSurface.LED_MODE_VOLUME, 0, model.getCurrentTrackBank ());

        model.addTrackBankObserver (this::switchBanks);

        this.sendIndex = sendIndex;
    }


    /** {@inheritDoc} */
    @Override
    public void setValue (final int index, final int value)
    {
        this.model.getCurrentTrackBank ().getItem (index).getSendBank ().getItem (this.sendIndex).setValue (value);
    }


    /** {@inheritDoc} */
    @Override
    public int getKnobValue (final int index)
    {
        if (this.model.isEffectTrackBankActive ())
            return 0;
        final ITrack track = this.model.getCurrentTrackBank ().getItem (index);
        return track.doesExist () ? track.getSendBank ().getItem (this.sendIndex).getValue () : -1;
    }
}
