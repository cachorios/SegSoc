import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import './search-bar.js';
import './utils-mixin.js';
import './../../styles/shared-styles.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';

class SC_find extends PolymerElement {
    static get template(){
        return html`
     <style include="shared-styles">
        :host {
            height: 100%;
            /*width: calc(100% - 2 * 1rem);*/
            --top-bar-height: 70px;
            --titulo-height: 50px;
            display: flex;
            flex-direction: column;
            /*margin: 1rem;*/

            /*flex: 1 1 100%;*/
            /*align-self: stretch;*/
            /*padding: 0 0.5rem;*/
        }

        /*::slotted([slot=grid]) {*/
             /*height: calc(99% - var(--top-bar-height));*/
        /*}*/
        vaadin-grid {
            height: calc(100% - var(--top-bar-height));
        }
        [part="abm-content"]{
            height: calc(100% - var(--titulo-height));
        }

    </style>

        <h4 id="titulo" part="titulo" theme="abm"></h4>
        <div part="abm-content" theme="abm">
            <search-bar id="searchbar" part="searchbar" theme="find"></search-bar>
            <vaadin-grid id="grid" theme="lar" slot="grid"></vaadin-grid>
        </div>
`;
    }

        static get is() {
            return 'sc-find'
        }
            // ready() {
            //     super.ready();
            //     // alert("sc-find llego!!!");
            // }
}
       window.customElements.define(SC_find.is, SC_find);

