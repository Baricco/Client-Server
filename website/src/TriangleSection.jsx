import "./styles/TriangleSection.css"

export function TriangleSection(a, b) {
    if (b < 1) {
        return ( <
            div id = "InnerDiv" >
            <
            div id = "TrapezoidShape1" >
            <
            center >
            <
            h1 > { a } < /h1>   <
            /center >  <
            /div>   <
            /div>
        );
    } else {
        return ( <
            div id = "InnerDiv" >
            <
            div id = "TrapezoidShape2" >
            <
            center >
            <
            h1 id = "h1Bello" > { a } < /h1>   <
            /center >  <
            /div>   <
            /div>
        );
    }
}

export default TriangleSection;