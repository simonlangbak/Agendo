import { MenuItem } from "primeng/api";

export class BoardMenuItem {
    id: number | undefined;
    label: string;
    icon: string | undefined;
    optionItems: MenuItem[] | undefined;

    constructor(id: number | undefined, label: string, icon: string | undefined, optionsItems: MenuItem[] | undefined) {
        this.id = id;
        this.label = label;
        this.icon = icon;
        this.optionItems = optionsItems;
    };
}