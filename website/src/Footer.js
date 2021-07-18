import "./styles/Footer.css"

function Footer(){
    return (
        <div id="footer">
            <div id="footer_content">
                <div id="footer_left">
                    <p className="font_l family_title">HLR Messaging Service</p>
                    <p className="font_s">We give you what you need</p>
                    <input className="input_email font_s" id="footer_emailInput" type="email" placeholder="Enter your email"/>
                    <button className="input_button font_s" id="footer_emailBtn">Subscribe</button>
                </div>
                <div id="footer_right">
                    <ul>
                        <li>aa</li>
                        <li>b</li>
                        <li>c</li>
                        <li>d</li>
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default Footer;