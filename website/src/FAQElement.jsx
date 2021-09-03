import './styles/FAQElement.css';

export default class FAQElement {
    constructor(question, answer){

        this.question = question;

        this.answer = answer;
    }

    render() {
        return (
            <li>
                <h4 className="family_title font_m kerning_s question">{this.question}</h4>
                <p className="family_text font_m kerning_s">
                    {this.answer}
                </p>
            </li>
        );
    }
}