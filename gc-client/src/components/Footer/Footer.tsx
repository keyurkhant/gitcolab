import {Container, Col, Row} from 'react-bootstrap';

const Footer = () => {

    return (
        <footer>
            <Container fluid >
                <Row>
                    <Col className='text-center' >
                        <hr style={{height:2, color:'black', backgroundColor:'black', borderColor : 'black',}} ></hr>
                        Github Colab | CSCI 5308
                    </Col>
                </Row>
            </Container>
        </footer>
    )
}

export default Footer;