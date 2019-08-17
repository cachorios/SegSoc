import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import './form-buttons-bar.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';

class AbmForm extends PolymerElement {
    static get template() {
        return html`
   <style>
          :host {
                display: flex;
               flex-direction: column;
               flex: auto;
               height: 100%;
               /*background-color: #ef6c00;*/
               /*width: 400px;*/

          }
          [part="content"] ::slotted(flow-component-renderer) {
                         height: 100%;
                    }

          vaadin-form-layout {
            overflow: auto;
            flex: auto;
            min-width: 30em;

          }
    </style>
    <h3 id="titulo">Titulo</h3>
    <vaadin-form-layout id="form">

    </vaadin-form-layout>

    <form-buttons-bar id="botonera"></form-buttons-bar>
`;
}

  static get is() {
    return 'lar-abm-form';
  }

  ready() {
    super.ready();
    this.$.form.addEventListener('change', e => {
      this.$.botonera.$.save.disabled = false;
    });
  }
}

  window.customElements.define(AbmForm.is, AbmForm);
