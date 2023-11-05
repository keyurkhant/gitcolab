import { Container, Navbar, Nav } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { LinkContainer } from 'react-router-bootstrap';
import { logout, selectUser } from '../../redux/userSlice';
import { useNavigate } from 'react-router';

const Header = () => {
    const isAuth = useSelector(selectUser);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleSignOut = () => {
        dispatch(logout());
        localStorage.removeItem('token');
        localStorage.removeItem('email');
        localStorage.removeItem('username');
        localStorage.removeItem('id');
        localStorage.removeItem('refreshToken');
        navigate('/');
    }

    return (
        <header>
            <Navbar bg="dark" variant="dark" expand="lg" >
                <Container className=''>

                    <LinkContainer to='/dashboard'>
                        <Navbar.Brand >Github Colab</Navbar.Brand>
                    </LinkContainer>

                    <Navbar.Toggle aria-controls="basic-navbar-nav" />

                    <Navbar.Collapse id="basic-navbar-nav">
                        {!isAuth ? (
                            <Nav className='ms-auto'>
                                <LinkContainer to='/login'>
                                    <Nav.Link>Sign in <i className="fas fa-sign-in"></i></Nav.Link>
                                </LinkContainer>
                            </Nav>
                        )
                            :
                            (
                                <>
                                    <Nav className='ms-auto'>
                                        <LinkContainer to='/dashboard'>
                                            <Nav.Link>Dashboard</Nav.Link>
                                        </LinkContainer>

                                        <LinkContainer to='/integration'>
                                            <Nav.Link>Integration</Nav.Link>
                                        </LinkContainer>

                                        <LinkContainer to='/explore'>
                                            <Nav.Link>Explore</Nav.Link>
                                        </LinkContainer>
                                    </Nav>

                                    <Nav className='ms-auto'>
                                        <LinkContainer to='/profile'>
                                            <Nav.Link>
                                                <i className="fas fa-user"></i> Profile
                                            </Nav.Link>
                                        </LinkContainer>

                                        <Nav.Link onClick={handleSignOut}><i className="fas fa-sign-out"></i> Sign Out</Nav.Link>
                                    </Nav>
                                </>
                            )}
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </header>
    )
}

export default Header;